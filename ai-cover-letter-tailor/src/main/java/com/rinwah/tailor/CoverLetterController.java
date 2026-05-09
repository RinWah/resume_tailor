@RestController
@RequestMapping("/api")
public class CoverLetterController {
    @Autowired
    private GeminiService geminiService;
    @PostMapping("/process-resume")
    public ResponseEntity<String> handleUpload(
        @RequestParm("file") MultipartFile file,
        @RequestParam("jobDescription") String jobDesc) {
            // 1. extract text (pdfbox/tess4j)
            String rawText = pdfService.extract(file);
            // 2. transform messy text to clean latex
            String latexResume = geminiService.transformToLatex(rawText);
            // 3. use LaTeX to write cover letter
            String coverLetter = geminiService.generateCoverLetter(latexResume, jobDesc);
            return ResponseEntity.ok(coverLetter);
        }
    }