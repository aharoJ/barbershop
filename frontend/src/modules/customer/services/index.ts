import { CustomerServiceImpl } from "./customer.service.impl";
import { CustomerService } from "./customer.service";

// Create a default instance
const customerService: CustomerService = new CustomerServiceImpl();

// Optionally export the interface and a default instance
export { customerService };
