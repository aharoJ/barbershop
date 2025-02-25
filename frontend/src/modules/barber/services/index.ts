import { BarberService } from "./barber.service";
import { BarberServiceImpl } from "./barber.service.impl";

// Create a default instance
const barberService: BarberService= new BarberServiceImpl();

// Optionally export the interface and a default instance
export { barberService };
