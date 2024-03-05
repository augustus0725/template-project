"use client"

import { Button } from "@/components/registry/new-york/ui/button"
import { ToastAction } from "@/components/registry/new-york/ui/toast"
import { useToast } from "@/components/registry/new-york/ui/use-toast"

export default function ToastDestructive() {
  const { toast } = useToast()

  return (
    <Button
      variant="outline"
      onClick={() => {
        toast({
          variant: "destructive",
          title: "Uh oh! Something went wrong.",
          description: "There was a problem with your request.",
          action: <ToastAction altText="Try again">Try again</ToastAction>,
        })
      }}
    >
      Show Toast
    </Button>
  )
}
