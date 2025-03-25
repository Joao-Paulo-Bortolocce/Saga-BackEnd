
package sistema.saga.sagabackend.repository.util;

import sistema.saga.sagabackend.model.Pessoa;

import java.util.List;

public interface IDAL <T>{
    public boolean gravar(T entidade);
    public boolean alterar(T entidade);
    public boolean apagar(T entidade);
    public T get(int id);

    List<Pessoa> get(String filtro);
}
