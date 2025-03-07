"use client";
import { useState } from "react";
import { useRouter } from "next/navigation";
import { ownerService } from "@/modules/owner/services";
import { Button } from "@/modules/shadcn/ui/button";
import {
  Card,
  CardHeader,
  CardContent,
  CardFooter,
} from "@/modules/shadcn/ui/card";
import { Alert, AlertTitle, AlertDescription } from "@/modules/shadcn/ui/alert";
import { AlertTriangleIcon, Loader2Icon, ArrowLeftIcon } from "lucide-react";
import { motion } from "framer-motion";

export default function DeleteOwnerProfilePage() {
  const router = useRouter();
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const handleDelete = async () => {
    const confirmed = window.confirm(
      "This will permanently remove all your data. Confirm irreversible deletion:",
    );
    if (!confirmed) return;

    setLoading(true);
    setError(null);
    try {
      await ownerService.deleteOwnerProfile();
      router.push("/");
    } catch (err) {
      console.error("Delete error:", err);
      setError("Deletion failed. Please try again or contact support.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <motion.div
      initial={{ opacity: 0 }}
      animate={{ opacity: 1 }}
      className="min-h-screen bg-gradient-to-br from-gray-50 to-blue-50 flex items-center justify-center p-4"
    >
      <Card className="w-full max-w-md shadow-xl rounded-2xl border-0 bg-white/95 backdrop-blur-sm">
        <CardHeader className="space-y-4 text-center">
          <motion.div
            initial={{ scale: 0.8 }}
            animate={{ scale: 1 }}
            className="flex justify-center"
          >
            <AlertTriangleIcon className="h-16 w-16 text-destructive/80" />
          </motion.div>
          <h1 className="text-3xl font-bold text-foreground tracking-tight">
            Delete Account
          </h1>
        </CardHeader>

        <CardContent className="space-y-6">
          <Alert
            variant="destructive"
            className="border-l-4 border-destructive"
          >
            <AlertTriangleIcon className="h-5 w-5" />
            <AlertTitle>Irreversible Action</AlertTitle>
            <AlertDescription className="text-balance">
              All associated data including appointments, business details, and
              customer records will be permanently erased.
            </AlertDescription>
          </Alert>

          {error && (
            <Alert variant="destructive">
              <AlertTriangleIcon className="h-5 w-5" />
              <AlertDescription>{error}</AlertDescription>
            </Alert>
          )}
        </CardContent>

        <CardFooter className="flex flex-col gap-3">
          <Button
            onClick={handleDelete}
            variant="destructive"
            size="lg"
            className="w-full gap-2 transition-all hover:shadow-destructive/20 hover:shadow-lg"
            disabled={loading}
          >
            {loading ? (
              <>
                <Loader2Icon className="h-5 w-5 animate-spin" />
                Processing Deletion...
              </>
            ) : (
              "Permanently Delete Account"
            )}
          </Button>

          <Button
            onClick={() => router.back()}
            variant="ghost"
            size="lg"
            className="w-full text-muted-foreground hover:text-foreground gap-2"
            disabled={loading}
          >
            <ArrowLeftIcon className="h-5 w-5" />
            Return to Safety
          </Button>
        </CardFooter>
      </Card>
    </motion.div>
  );
}
