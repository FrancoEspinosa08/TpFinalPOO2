package inmuebleYUsuario;


import observer.Reserva;

public class Email {
	private String inbox;
    private Reserva attachment;
   
    
    public Email() {
	}

	public String getInbox() {
        return inbox;
    }

    public void setInbox(String mensaje, Reserva reserva){
        this.inbox = mensaje;
        this.attachment = reserva;
    }

    public void setInbox(String mensaje) {
        this.inbox = mensaje;
    }

    public Reserva getAttachment() {
        return attachment;
    }

    public void setAttachment(Reserva attachment) {
        this.attachment = attachment;
    }
}
