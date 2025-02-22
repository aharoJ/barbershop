// src/modules/owner/services/index.ts
import { OwnerServiceImpl } from "./owner.service.impl";
import { OwnerService } from "./owner.service";

// Create a default instance
const ownerServiceImpl: OwnerService = new OwnerServiceImpl();

// Optionally export the interface and a default instance
export { ownerServiceImpl };
