import type { Metadata } from "next";
import "./globals.css";

export const metadata: Metadata = {
  title: "BarberOS",
  description:
    "The open-source platform for barbershop discovery, booking, and management.",
};

export default function RootLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <html lang="en" data-theme="dark">
      <body>{children}</body>
    </html>
  );
}
