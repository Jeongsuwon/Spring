package smart.board;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FileVO {
	private int id;
	private String filename, filepath;
	private List<FileVO> fileList;
}
