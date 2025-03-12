// @/modules/image/services/index.ts
import { ImageService } from "./image.service";
import { ImageServiceImpl } from "./image.service.impl";

// Create a default instance
const imageService: ImageService = new ImageServiceImpl();

// Optionally export the interface and a default instance
export { imageService };
