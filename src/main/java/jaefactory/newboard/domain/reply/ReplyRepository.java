package jaefactory.newboard.domain.reply;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply,Integer> {

    List<Reply> findAllByBoardId(int boardId);

    @Modifying
    @Query(value="INSERT INTO reply(user_id, board_id, content, createDate) VALUES(?1, ?2, ?3, now())", nativeQuery = true)
    int mSave(int userId, int boardId, String content); // 업데이트된 행의 개수를 리턴해줌.
}
