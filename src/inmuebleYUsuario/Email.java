package inmuebleYUsuario;

import observer.Inmueble;

public class Email {
	private String inbox;
    private Inmueble attachment;
   
    
    public Email() {
	}

	public String getInbox() {
        return inbox;
    }

    public void setInbox(String mensaje, Inmueble inmueble){
        this.inbox = mensaje;
        this.attachment = inmueble;
    }

    public void setInbox(String mensaje) {
        this.inbox = mensaje;
    }

    public Inmueble getAttachment() {
        return attachment;
    }

    public void setAttachment(Inmueble attachment) {
        this.attachment = attachment;
    }
}
