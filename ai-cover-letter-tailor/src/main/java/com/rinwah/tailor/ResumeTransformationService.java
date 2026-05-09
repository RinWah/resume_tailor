public string transformToLatex(String rawOcrText) {
    // messy OCR to gemini
    String prompt = "the following text is raw ocr output from a resume pdf. 
                    please clean it up and convert it into a well-structured
                    laTeX document using the 'resume document class. ensure
                    all special characters are escaped. here's the text: " +
                    rawOcrText;
    // gemini service call to api
    return geminiService.callAi(prompt);