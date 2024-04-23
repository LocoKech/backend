package com.rentalCar.car;

import com.rentalCar.booking.Booking;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CarSpec {

    public static Specification<Car> filterBy(CarFilter carFilter) {
        return Specification
                .where(hasMark(carFilter.getMark()))
                .and(hasType(carFilter.getType()))
                .and(hasNumberOfSeats(carFilter.getNumberOfSeats()))
                .and(hasNoBookingOnDate(carFilter.getReservationDate()))
                .and(hasPriceGreaterThan(carFilter.getPriceFrom()))
                .and(hasPriceLessThan(carFilter.getPriceTo()));
    }

    private static Specification<Car> hasMark(String mark) {
        return (root, query, cb) -> mark == null || mark.isEmpty() ? cb.conjunction() : cb.equal(root.get("mark"), mark);
    }

    private static Specification<Car> hasType(String type) {
        return (root, query, cb) -> type == null || type.isEmpty() ? cb.conjunction() : cb.equal(root.get("type"), type);
    }

    private static Specification<Car> hasNumberOfSeats(Integer numberOfSeats) {
        return (root, query, cb) -> numberOfSeats == null ? cb.conjunction() : cb.equal(root.get("numberOfSeats"), numberOfSeats);
    }

    private static Specification<Car> hasNoBookingOnDate(LocalDate reservationDate) {
        return (root, query, cb) -> {
            if (reservationDate == null) {
                return cb.conjunction(); // No reservation date specified, so all cars are considered available
            } else {
                Subquery<Long> subquery = query.subquery(Long.class);
                Root<Booking> bookingRoot = subquery.from(Booking.class);
                subquery.select(cb.count(bookingRoot.get("id")));
                subquery.where(
                        cb.equal(bookingRoot.get("car"), root),
                        cb.lessThanOrEqualTo(bookingRoot.get("startDate"), reservationDate),
                        cb.greaterThanOrEqualTo(bookingRoot.get("endDate"), reservationDate)
                );

                return cb.equal(subquery, 0L); // Cars with no bookings on the specified date
            }
        };
    }


    private static Specification<Car> hasPriceGreaterThan(Long priceFrom) {
        return (root, query, cb) -> priceFrom == null ? cb.conjunction() : cb.greaterThan(root.get("price"), BigDecimal.valueOf(priceFrom));
    }

    private static Specification<Car> hasPriceLessThan(Long priceTo) {
        return (root, query, cb) -> priceTo == null ? cb.conjunction() : cb.lessThan(root.get("price"), BigDecimal.valueOf(priceTo));
    }
}
