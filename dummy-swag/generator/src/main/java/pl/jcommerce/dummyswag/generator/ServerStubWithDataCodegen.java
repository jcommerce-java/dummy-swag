package pl.jcommerce.dummyswag.generator;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang3.StringEscapeUtils;

import io.swagger.codegen.CodegenOperation;
import io.swagger.codegen.languages.SpringCodegen;
import io.swagger.models.Model;
import io.swagger.models.Operation;
import io.swagger.models.Swagger;

public class ServerStubWithDataCodegen extends SpringCodegen {
	
    @Override
    public CodegenOperation fromOperation(String path, String httpMethod, Operation operation, Map<String, Model> definitions, Swagger swagger) {
        CodegenOperation codegenOperation = super.fromOperation(path, httpMethod, operation, definitions, swagger);

        if (codegenOperation.examples != null) {
            for (Iterator<Map<String, String>> it = codegenOperation.examples.iterator(); it.hasNext(); ) {
                final Map<String, String> example = it.next();
                final String contentType = example.get("contentType");
                if (contentType == null || !contentType.startsWith("application/json")) {
                    it.remove();
                } else {
                    String json = example.get("example");
                    example.put("example", StringEscapeUtils.escapeJson(json));
                }
            }
        }
        if (codegenOperation.pathParams != null) {
        	codegenOperation.pathParams.stream().forEach(param -> param.allowableValues = Collections.emptyMap());
        }

        return codegenOperation;
    }

}
