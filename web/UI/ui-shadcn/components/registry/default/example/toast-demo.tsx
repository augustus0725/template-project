"use client"

import { Button } from "@/components/registry/default/ui/button"
import { ToastAction } from "@/components/registry/default/ui/toast"
import { useToast } from "@/components/registry/default/ui/use-toast"

export default function ToastDemo() {
  const { toast } = useToast()

  return (
    <Button
      variant="outline"
      onClick={() => {
        toast({
          title: "Scheduled: Catch up ",
          description: "Friday, February 10, 2023 at 5:57 PM",
          action: (
            <ToastAction altText="Goto schedule to undo">Undo</ToastAction>
          ),
        })
      }}
    >
      Add to calendar
    </Button>
  )
}
