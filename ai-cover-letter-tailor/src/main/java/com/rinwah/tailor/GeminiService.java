import com.google.cloud.vertexai.VertexAI;
import com.google.cloud.vertexai.generativeai.GenerativeModel;
import com.google.cloud.vertexai.generativeai.ResponseHandler;
import org.springframework.stereotype.Service;

@Service
public class GeminiService {
    // replace this or set it as an environment variable later.
    // PUT API KEY HERE
    @Value("${GEMINI_API_KEY}")
    private String apiKey;
    public String generateCoverLetter(String resumeText, String jobDescription) {
        try (VertexAI vertexAi = new VertexAI("resume-tailor", "us-central1"))
            GenerativeModel model = new GenerativeModel("gemini-1.5-flash", vertexAi);
        String prompt = String.format(
            "you are an expert career coach with an enthusiastic and modern tone. " + 
            "using the following resume and job description, write a tailored cover letter. " +
            "focus on bridging the gap between the candidate's skills and the company's needs. " + 
            "\n\nresume: \n%s\n\njob description:\n%s", resumeText, jobDescription);
        var response = model.generateContent(prompt);
        return ResponseHandler.getText(response);
    } catch (Exception e) {
        return "error generating cover letter: " + e.getMessage();
    }
}