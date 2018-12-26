package nl.dahlberg.movie.domain.service;

import lombok.AllArgsConstructor;
import nl.dahlberg.movie.domain.model.MovieTitle;
import nl.dahlberg.movie.domain.repository.MovieTitleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class MovieTitleService {
    private final MovieTitleRepository movieTitleRepository;

    public void addMovieTitles(final Stream<MovieTitle> movieTitleStream) {
        int[] counter = {0};
        long[] now = {System.currentTimeMillis()};
        movieTitleRepository.saveAllBatched(movieTitleStream
                .peek(movieTitle -> {
                    if (counter[0] % 1000 == 0 && counter[0] > 0) {
                        System.out.println("Save " + counter[0] + " in " + (System.currentTimeMillis() - now[0]) + "ms");
                        now[0] = System.currentTimeMillis();
                    }
                    counter[0]++;
                })
                ::iterator);
    }

    public Page<MovieTitle> getMovieTitles(final Pageable pageable) {
        return movieTitleRepository.findAll(pageable);
    }
}
