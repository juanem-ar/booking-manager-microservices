package com.booking_manager.payment.services.impl;

import com.booking_manager.payment.mappers.ICouponMapper;
import com.booking_manager.payment.models.dtos.BaseResponse;
import com.booking_manager.payment.models.dtos.CouponRequestDto;
import com.booking_manager.payment.models.dtos.CouponResponseDto;
import com.booking_manager.payment.models.dtos.CouponResponseDtoWithEntity;
import com.booking_manager.payment.models.entities.CouponEntity;
import com.booking_manager.payment.models.entities.DeletedEntity;
import com.booking_manager.payment.models.enums.ETypes;
import com.booking_manager.payment.repositories.ICouponRepository;
import com.booking_manager.payment.repositories.IDeleteRepository;
import com.booking_manager.payment.services.ICouponService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class CouponServiceImpl implements ICouponService {
    private final ICouponRepository iCouponRepository;
    private final ICouponMapper iCouponMapper;
    private final IDeleteRepository iDeleteRepository;

    /**
     * Create a {@code CouponEntity} if is already not exists. This method will set the {@code deleted} attribute to false and {@code usageCount} in 0 before to save the entity.
     * The returned value will be a {@code CouponResponseDto}.
     *
     * @param dto the coupon body.
     */
    @Override
    public CouponResponseDto createCoupon(CouponRequestDto dto) throws BadRequestException {
        existsCouponEntityByCode(dto);
        var entity = iCouponMapper.toEntity(dto);
        entity.setDeleted(Boolean.FALSE);
        entity.setUsageCount(0);
        var savedEntity = iCouponRepository.save(entity);
        log.info("Coupon Entity Saved: {}", savedEntity);
        return iCouponMapper.toDto(savedEntity);
    }
    /**
     * Gets all {@code CouponEntity} if is already exists.
     *
     *
     */
    @Override
    public List<CouponResponseDto> getAllCoupons() {
        var entityList = iCouponRepository.findAllByDeleted(false);
        if (entityList.isEmpty())
            return new ArrayList<>();
        return iCouponMapper.toDtoList(entityList);
    }
    /**
     * Edit a {@code CouponEntity} if is already exists. This method will set the attributes mapped by {@code CouponRequestDto}.
     *
     * @param id the coupon code id.
    *  @param dto the coupon body to map.
     */
    @Override
    public CouponResponseDto editCoupon(CouponRequestDto dto, Long id) throws BadRequestException {
        existsCouponEntityByCode(dto);
        var entity = getCouponEntityNotDeletedById(id);
        var mappedEntity = iCouponMapper.updateToEntity(dto, entity);
        var savedEntity = iCouponRepository.save(mappedEntity);
        return iCouponMapper.toDto(savedEntity);
    }
    /**
     * Delete a {@code CouponEntity} if is already exists. This method will set the {@code deleted} attribute to true.
     * Besides a new {@code DeletedEntity} will be registered and the method return a success message.
     *
     * @param id the coupon code id.
     */
    @Override
    public String deleteCoupon(Long id) {
        var entity = getCouponEntityNotDeletedById(id);
        entity.setDeleted(Boolean.TRUE);
        var savedEntity = iCouponRepository.save(entity);
        var deletedEntity = DeletedEntity.builder()
                .couponId(savedEntity.getId()).build();
        var savedDeletedEntity = iDeleteRepository.save(deletedEntity);
        log.info("Deleted Coupon Entity: {}", savedEntity);
        log.info("new Deleted Entity added: {}", savedDeletedEntity);
        return "Deleted coupon.";
    }
    /**
     * Gets a {@code CouponEntity} if is already exists.
     *
     * @param   id   the coupon code id.
     */
    @Override
    public CouponResponseDto getCouponById(Long id) {
        var entity = getCouponEntityNotDeletedById(id);
        return iCouponMapper.toDto(entity);
    }
    /**
     * Method to get a {@code CouponEntity} if is already exists.
     *
     * @param   id   the coupon id.
     */
    public CouponEntity getCouponEntityNotDeletedById(Long id){
        var existsEntity = iCouponRepository.existsByIdAndDeleted(id, false);
        if (existsEntity) {
            return  iCouponRepository.getReferenceById(id);
        }else{
            throw new IllegalArgumentException("Invalid coupon code.");
        }
    }
    /**
     * Method to get a {@code CouponEntity} if is already exists.
     *
     * @param   code   the coupon code.
     */
    public CouponEntity getCouponEntityNotDeletedByCode(String code){
        var existsEntity = iCouponRepository.existsByCodeAndDeleted(code, false);
        if (existsEntity) {
            return  iCouponRepository.getReferenceByCodeAndDeleted(code, false);
        }else{
            throw new IllegalArgumentException("Invalid coupon code.");
        }
    }
    /**
     * Method to get a boolean if is already exists.
     *
     * @param   dto   the coupon request body.
     */
    public void existsCouponEntityByCode(CouponRequestDto dto) throws BadRequestException {
        var existsEntity = iCouponRepository.existsByCodeAndDeleted(dto.getCode(), false);
        if (existsEntity)
            throw new BadRequestException("Coupon already exists.");
    }
    /**
     * Method to set discount on a total amount.
     *
     * @param totalAmount the total amount.
     * @param code        the coupon code.
     */
    public CouponResponseDtoWithEntity applyDiscount(Double totalAmount, LocalDate bookingCheckIn, LocalDate bookingCheckOut, Long duration, String code) throws BadRequestException {
        var entity = getCouponEntityNotDeletedByCode(code);
        var usageCouponCount = entity.getUsageCount();
        var usageCouponLimit = entity.getUsageLimit();

        LocalDate today = LocalDate.now();
        if (usageCouponCount <= usageCouponLimit &&
                today.isEqual(entity.getExpirationDate()) ||
                usageCouponCount <= usageCouponLimit &&
                        today.isBefore(entity.getExpirationDate())){
            return getaDoubleByTotalAmountAndCouponType(totalAmount, bookingCheckIn, bookingCheckOut, duration, entity);
        }else{
            throw new BadRequestException("Coupon code has expired.");
        }
    }

    /**
     * This method validates the discount {@code EType} and returns the total amount with the discount applied. Once applied, it adds a new amount to the usage counter. If this reaches the limit, the coupon is eliminated.
     *
     * @param totalAmount     the total amount.
     * @param bookingCheckIn  the booking check-in.
     * @param bookingCheckOut the booking check-out.
     * @param duration        the booking duration.
     * @param entity          the coupon entity.
     * @return Double totalAmount - Coupon amount
     */
    private CouponResponseDtoWithEntity getaDoubleByTotalAmountAndCouponType(Double totalAmount, LocalDate bookingCheckIn, LocalDate bookingCheckOut, Long duration, CouponEntity entity) throws BadRequestException {
        var totalResult = totalAmount;
        var usageCouponCount = entity.getUsageCount();

        var errorList = optionalDateValidations(bookingCheckIn, bookingCheckOut, duration, entity);
        if (errorList !=null && !errorList.hastErrors()){
            if (entity.getType().equals(ETypes.AMOUNT))
                totalResult -= entity.getAmount();
            else if(entity.getType().equals(ETypes.PERCENTAGE))
                totalResult -= totalResult * entity.getAmount() / 100.0;

            entity.setUsageCount(usageCouponCount+=1);

            if (entity.getUsageCount() == entity.getUsageLimit()) {
                entity.setDeleted(Boolean.TRUE);
                throw new BadRequestException("Your payment amount is bigger than total amount or this Coupon has been eliminated upon reaching its limit of uses");
            }
            var savedEntity = iCouponRepository.save(entity);

            var result = CouponResponseDtoWithEntity.builder()
                            .totalAmount(totalResult)
                                    .entity(savedEntity)
                                            .build();
            log.info("Total amount changed: {}", totalResult);
            log.info("Total amount changed by type: {}", entity.getType());
            log.info("Coupon count increased: {}", savedEntity.getUsageCount());
            return result;
        }else{
            throw new BadRequestException("Error: " + Arrays.stream(errorList.errorMessage()).toList());
        }
    }
    /**
     * This method validates the optional parameters of the discount coupon.
     *     If the conditions are not met, error list will be created.
     *
     * @param   bookingCheckIn   the booking check-in.
     * @param   bookingCheckOut   the booking check-out.
     * @param   duration   the booking duration.
     * @param   entity   the coupon entity.
     */
    public BaseResponse optionalDateValidations(LocalDate bookingCheckIn, LocalDate bookingCheckOut, Long duration, CouponEntity entity){
        var checkInDateAfter = entity.getCheckInDateAfter();
        var checkOutDateBefore = entity.getCheckOutDateBefore();
        var minimumDays = entity.getMinimumDays();

        var errorList = new ArrayList<>();
        if (checkInDateAfter != null && !bookingCheckIn.isAfter(checkInDateAfter))
            errorList.add("The discount coupon does not apply to your check-in date.");
        if (checkOutDateBefore != null && !bookingCheckOut.isBefore(checkOutDateBefore))
            errorList.add("The discount coupon does not apply to your check-out date.");
        if (minimumDays != 0 && minimumDays > duration)
            errorList.add("Insufficient booking days to obtain this discount.");
        return !errorList.isEmpty() ? new BaseResponse(errorList.toArray(new String[0])) : new BaseResponse(null);
    }

}
