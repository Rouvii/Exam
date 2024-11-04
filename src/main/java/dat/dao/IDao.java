package dat.dao;

import java.util.List;

public interface IDao<T> {
    public List<T> getAll();
    public T getById(int id);
    public T create(T t);
    public T update(int id, T update);
    public T delete(long id);
}
