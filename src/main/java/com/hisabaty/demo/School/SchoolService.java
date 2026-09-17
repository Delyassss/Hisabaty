import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor // this is an annotation to inject the dependencies into the constructor  so no need for @Autowired

public class SchoolService
{
    private final SchoolRepository schoolRepository;
}