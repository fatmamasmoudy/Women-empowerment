package tn.esprit.spring.entities;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Chat  implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8393860672677060796L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String Content;
	private String Sender;
	private MessageType type;
	
	public enum MessageType{
		Chat,Leave,Join;
	}
}

