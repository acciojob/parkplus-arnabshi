package com.driver.services.impl;


import com.driver.model.Payment;
import com.driver.model.PaymentMode;
import com.driver.model.Reservation;
import com.driver.repository.PaymentRepository;
import com.driver.repository.ReservationRepository;
import com.driver.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    ReservationRepository reservationRepository2;
    @Autowired
    PaymentRepository paymentRepository2;

    @Override
    public Payment pay(Integer reservationId, int amountSent, String mode) throws Exception {

        Payment payment = new Payment();
        Reservation reservation = reservationRepository2.findById(reservationId).get();
        int price = reservation.getSpot().getPricePerHour();
        int bill = reservation.getNumberOfHours()*price;
        if(amountSent < bill)
        {

            // If the amount sent is less than the bill, the system should throw an exception "Insufficient Amount".
            throw new Exception("Insufficient Amount");
        }

        // the system should validate the payment mode entered by the user and only accept "cash", "card", or "UPI" as valid modes of payment.
        // Note that the characters of these strings can be in uppercase or lowercase. For example "cAsH" is a valid mode of payment.

        if(mode.equalsIgnoreCase("CASH") || mode.equalsIgnoreCase("CARD")|| mode.equalsIgnoreCase("UPI"))
        {

            // Setting payment attributes
            if(mode.equalsIgnoreCase("CASH"))
            {
                payment.setPaymentMode(PaymentMode.CASH);
            }
            else if (mode.equalsIgnoreCase("CARD"))
            {
                payment.setPaymentMode(PaymentMode.CARD);

            } else if (mode.equalsIgnoreCase("UPI")) {
                payment.setPaymentMode(PaymentMode.UPI);

            }

            // The system should also update the payment attributes for the reservation after a successful payment.

            payment.setPaymentCompleted(true);
            payment.setReservation(reservation);

            // Setting reservation attributes
            reservation.setPayment(payment);


            reservationRepository2.save(reservation);

            return payment;
        }
        else
        {
            // If the user enters a mode other than these, the system should throw an exception "Payment mode not detected".
            throw new Exception("Payment mode not detected");
        }

    }
}