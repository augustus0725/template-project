"use client"

import { Button } from "@/components/registry/new-york/ui/button"
import { useToast } from "@/components/registry/new-york/ui/use-toast"

export default function ToastSimple() {
  const { toast } = useToast()

  return (
    <Button
      variant="outline"
      onClick={() => {
        toast({
          description: "Your message has been sent.",
        })
      }}
    >
      Show Toast
    </Button>
  )
}
