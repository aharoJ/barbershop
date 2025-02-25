// @/modules/seat/services/index.ts

import { SeatService } from "./seat.service";
import { SeatServiceImpl } from "./seat.service.impl";

// Create the real service
const seatService: SeatService = new SeatServiceImpl();

export { seatService };
