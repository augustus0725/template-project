import { Button } from "@/components/registry/default/ui/button"
import { Textarea } from "@/components/registry/default/ui/textarea"

export default function TextareaWithButton() {
  return (
    <div className="grid w-full gap-2">
      <Textarea placeholder="Type your message here." />
      <Button>Send message</Button>
    </div>
  )
}
