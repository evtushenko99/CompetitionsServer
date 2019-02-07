package model;

public class Seat {
    private int seats_id;
    private int booked_seats;
    private int free_seats;
    private int price_for_seat;

    public Seat(int seats_id, int booked_seats, int free_seats, int price_for_seat){
        this.seats_id = seats_id;
        this.booked_seats = booked_seats;
        this.free_seats = free_seats;
        this.price_for_seat = price_for_seat;
    }

    public int getSeats_id(){
        return seats_id;
    }

    public void setSeats_id(int seats_id){
        this.seats_id = seats_id;
    }

    public int getBooked_seats(){
        return booked_seats;
    }

    public void setBooked_seats(int booked_seats){
        this.booked_seats = booked_seats;
    }

    public int getFree_seats(){
        return free_seats;
    }

    public void setFree_seats(int free_seats){
        this.free_seats = free_seats;
    }

    public int getPrice_for_seat(){
        return price_for_seat;
    }

    public void setPrice_for_seat(int price_for_seat){
        this.price_for_seat = price_for_seat;
    }

    @Override
    public String toString(){
        return "ID: " + getSeats_id() +
                "Количество свободных мест: " + getFree_seats() +
                "Количество занятых мест: " + getBooked_seats() +
                "Стоимость билета: " + getPrice_for_seat();
    }
}
