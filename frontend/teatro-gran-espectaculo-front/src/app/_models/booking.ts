
export interface Booking {
    id?: number,
    customerEmail: string,
    customerName: string,
    customerPhone: string,
    bookingDate?: string,
    ticketId: number
    ticketType?: string,
    eventName?: string,
    eventDate?: string,
}