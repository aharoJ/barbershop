import { CustomerServiceImpl } from "./customer.service.impl";
import { CustomerService } from "./owner.service";


// Create a default instance
const customerServiceImpl: CustomerService = new CustomerServiceImpl();

// Optionally export the interface and a default instance
export { customerServiceImpl };
