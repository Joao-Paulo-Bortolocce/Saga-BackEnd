package sistema.saga.sagabackend.model;

import jakarta.persistence.*;

@Entity
public class Materia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(value="materia_id")
    private int materia_id;

    @Column(value="materia_nome")
    private int materia_nome;

    public Materia() {
        this.materia_id = 0;
        this.materia_nome = "";
    }

    public Materia(int materia_id, int materia_nome) {
        this.materia_id = materia_id;
        this.materia_nome = materia_nome;
    }

    public int getMateria_id() {
        return materia_id;
    }

    public void setMateria_id(int materia_id) {
        this.materia_id = materia_id;
    }

    public int getMateria_nome() {
        return materia_nome;
    }

    public void setMateria_nome(int materia_nome) {
        this.materia_nome = materia_nome;
    }
}
