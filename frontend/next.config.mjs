/** @type {import('next').NextConfig} */
const nextConfig = {
  output: "standalone",
  images: {
    unoptimized: process.env.NODE_ENV === "development",
    remotePatterns: [
      {
        protocol: "http",
        hostname: "localhost",
        port: "9000",
        pathname: "/barberos-uploads/**",
      },
      ...(process.env.STORAGE_CDN_HOST
        ? [
            {
              protocol: "https",
              hostname: process.env.STORAGE_CDN_HOST,
              pathname: "/**",
            },
          ]
        : []),
    ],
  },
  async rewrites() {
    const apiUrl = process.env.API_URL || "http://localhost:8080";
    return [
      { source: "/api/auth/:path*", destination: `${apiUrl}/api/auth/:path*` },
      {
        source: "/api/public/:path*",
        destination: `${apiUrl}/api/public/:path*`,
      },
      { source: "/api/:path*", destination: `${apiUrl}/api/:path*` },
      {
        source: "/oauth2/authorization/:path*",
        destination: `${apiUrl}/oauth2/authorization/:path*`,
      },
      {
        source: "/actuator/:path*",
        destination: `${apiUrl}/actuator/:path*`,
      },
    ];
  },
};

export default nextConfig;
