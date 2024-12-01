package com.example.spring_data_jdbc.repository;

import com.example.spring_data_jdbc.model.Book;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Repository
public class CrudRepositoryImpl implements CrudRepository<Book, Long> {

    private final JdbcTemplate jdbcTemplate;

    public CrudRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private final RowMapper<Book> mapper = (rs, rowNum) -> {
        Book book = new Book();
        book.setId(rs.getLong("id"));
        book.setTitle(rs.getString("title"));
        book.setAuthor(rs.getString("author"));
        book.setPublicationYear(rs.getInt("publication_year"));
        return book;
    };

    @Override
    public Book save(Book entity) {
        final String INSERT = "insert into  book (title, author, publication_year) values (?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                con -> {
                    PreparedStatement ps = con.prepareStatement(INSERT, new String[]{"id"});
                    ps.setString(1, entity.getTitle());
                    ps.setString(2, entity.getAuthor());
                    ps.setInt(3, entity.getPublicationYear());
                    return ps;
                }, keyHolder);
        long id = keyHolder.getKey().longValue();
        entity.setId(id);
        return entity;
    }

    @Override
    public Optional<Book> findById(Long id) {
        Book book;
        try {
            String SELECT = "select id, title, author, publication_year from book where id = ?";
            book = jdbcTemplate.queryForObject(SELECT, mapper, id);
        } catch (EmptyResultDataAccessException e) {
            book = null;
        }
        return Optional.ofNullable(book);
    }

    @Override
    public List<Book> findAll() {
        String SELECT = "select id, title, author, publication_year from book";
        List<Book> books = jdbcTemplate.query(SELECT, mapper);
        return books;
    }

    @Override
    public void update(Book entity) {
        String UPDATE_TITLE = "update book set title = ? where id = ? ";
        String UPDATE_AUTHOR = "update book set author = ? where id = ? ";
        String UPDATE_YEAR = "update book set publication_year = ? where id = ?";
        if (entity.getTitle() != null) jdbcTemplate.update(UPDATE_TITLE, entity.getTitle(), entity.getId());
        if (entity.getAuthor() != null) jdbcTemplate.update(UPDATE_AUTHOR, entity.getAuthor(), entity.getId());
        if (entity.getPublicationYear() != 0) jdbcTemplate.update(UPDATE_YEAR, entity.getPublicationYear(), entity.getId());
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update("delete from book where id = ?", id);
    }

    @Override
    public void delete(Book entity) {
        String DELETE = "delete from book where title = ? and author = ? and publication_year = ?";
        jdbcTemplate.update(DELETE, entity.getTitle(), entity.getAuthor(), entity.getPublicationYear());
    }
}
