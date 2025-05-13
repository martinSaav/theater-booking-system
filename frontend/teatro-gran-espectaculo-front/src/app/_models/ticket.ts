export interface Ticket {
    id: number,
    type: string,
    price: number,
    totalStock: number,
    availableStock: number,
    eventId: number
}