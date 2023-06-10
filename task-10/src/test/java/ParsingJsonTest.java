import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import ru.otus.gorenkov.models.dto.AuthorDto;
import ru.otus.gorenkov.models.dto.BookDto;
import ru.otus.gorenkov.models.dto.GenreDto;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class ParsingJsonTest {

    @Test
    public void shouldCorrectParseBookDto() throws IOException {

        String json = "{\"id\":\"647d85f761291a058140dea4\",\"name\":\"Властелин колец\",\"author\":{\"fullName\":\"Джон Роналд Толкин\"},\"genre\":{\"title\":\"фэнтези\"}}";
        ObjectMapper mapper = new ObjectMapper();
        BookDto bookDto = mapper.reader().forType(BookDto.class).readValue(json);

        BookDto expectedBookDto = new BookDto("647d85f761291a058140dea4",
                "Властелин колец", new AuthorDto("Джон Роналд Толкин"), new GenreDto("фэнтези"));

        assertThat(bookDto).usingRecursiveComparison().isEqualTo(expectedBookDto);
    }
}
