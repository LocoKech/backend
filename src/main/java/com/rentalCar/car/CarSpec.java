package com.rentalCar.car;

import com.rentalCar.booking.Booking;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class CarSpec {

    public static Specification<Car> filterBy(CarFilter carFilter) {
        return Specification
                .where(hasMark(carFilter.getMark()))
                .and(hasType(carFilter.getType()))
                .and(hasNumberOfSeats(carFilter.getNumberOfSeats()))
                .and(hasNoBookingInInterval(carFilter.getFromDate(),carFilter.getToDate()))
                .and(hasPriceGreaterThan(carFilter.getPriceFrom()))
                .and(hasPriceLessThan(carFilter.getPriceTo()))
                .and(hasAirConditioning(carFilter.isAirConditioning()))
                .and(hasAutomaticTransmission(carFilter.isAutomaticTransmission()))
                .and(hasNumberOfDoors(carFilter.getNumberOfDoors()))
                .and(hasReviewGreaterThan(carFilter.getReview()));
    }

    private static Specification<Car> hasMark(List<String> marks) {
        return (root, query, cb) -> marks == null || marks.isEmpty() ? cb.conjunction() : root.get("mark").in(marks);
    }

    private static Specification<Car> hasType(List<String> types) {
        return (root, query, cb) -> types == null || types.isEmpty() ? cb.conjunction() : root.get("type").in(types);
    }


    private static Specification<Car> hasNumberOfSeats(Integer numberOfSeats) {
        return (root, query, cb) -> numberOfSeats == null ? cb.conjunction() : cb.equal(root.get("numberOfSeats"), numberOfSeats);
    }

        public static Specification<Car> hasNoBookingInInterval(LocalDate fromDate, LocalDate toDate) {
            return (root, query, cb) -> {
                if (fromDate == null || toDate == null) {
                    return cb.conjunction();
                } else {
                    Subquery<Long> subquery = query.subquery(Long.class);
                    Root<Booking> bookingRoot = subquery.from(Booking.class);
                    subquery.select(cb.count(bookingRoot.get("id")));
                    subquery.where(
                            cb.equal(bookingRoot.get("car"), root),
                            cb.greaterThanOrEqualTo(bookingRoot.get("endDate"), fromDate),
                            cb.lessThanOrEqualTo(bookingRoot.get("startDate"), toDate)
                    );

                    return cb.equal(subquery, 0L);
                }
            };
        }




    private static Specification<Car> hasPriceGreaterThan(Long priceFrom) {
        return (root, query, cb) -> priceFrom == null ? cb.conjunction() : cb.greaterThan(root.get("price"), BigDecimal.valueOf(priceFrom));
    }

    private static Specification<Car> hasPriceLessThan(Long priceTo) {
        return (root, query, cb) -> priceTo == null ? cb.conjunction() : cb.lessThan(root.get("price"), BigDecimal.valueOf(priceTo));
    }

    public static Specification<Car> hasReviewGreaterThan(Double reviewFrom) {
        return (root, query, cb) -> reviewFrom == null ? cb.conjunction() : cb.greaterThanOrEqualTo(root.get("review"), reviewFrom);
    }

    public static Specification<Car> hasAutomaticTransmission(Boolean automaticTransmission) {
        return (root, query, cb) -> {
            if (automaticTransmission == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get("automaticTransmission"), automaticTransmission);
        };
    }

    public static Specification<Car> hasAirConditioning(Boolean airConditioning) {
        return (root, query, cb) -> {
            if (airConditioning == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get("airConditioning"), airConditioning);
        };
    }

    public static Specification<Car> hasNumberOfDoors(Long numberOfDoors) {
        return (root, query, cb) -> numberOfDoors == null ? cb.conjunction() : cb.equal(root.get("numberOfDoors"), numberOfDoors);
    }
}
