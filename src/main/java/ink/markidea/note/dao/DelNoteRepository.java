package ink.markidea.note.dao;

import ink.markidea.note.entity.DelNoteDo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface DelNoteRepository extends JpaRepository<DelNoteDo, Integer> {

    //username用来确保从该用户的笔记中查询
    //用于回收站中笔记的恢复
    DelNoteDo findByIdAndUsername(Integer id, String username);

    List<DelNoteDo> findAllByUsername(String username);

    //username用来确认文章是被发布者删除的
    @Transactional
    void deleteByIdAndUsername(Integer id, String username);

    @Transactional
    void deleteAllByUsername(String username);
}
