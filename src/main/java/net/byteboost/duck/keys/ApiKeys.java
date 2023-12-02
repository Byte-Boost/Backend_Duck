package net.byteboost.duck.keys;

import net.byteboost.duck.utils.SecretUtils;

public class ApiKeys {
    //OpenAI API
    public static final String OPENAI_API_KEY = "";

    //HuggingFace API
    public static final String HF_API_KEY = SecretUtils.getSecret("API_KEY");

    //Judge0 RapidAPI key
    public static final String RAPID_API_KEY = System.getenv("");
}