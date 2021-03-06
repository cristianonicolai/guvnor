package org.drools.ide.common.server.testscenarios.verifiers;

import junit.framework.TestCase;
import org.drools.SqlDateWrapper;
import org.drools.base.TypeResolver;
import org.drools.ide.common.client.modeldriven.testing.VerifyField;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.mockito.Mockito.mock;

public class FactFieldValueVerifierTest extends TestCase {
    public void testSQLDate() throws Exception {

        SqlDateWrapper sqlDateWrapper = new SqlDateWrapper();
        sqlDateWrapper.setSqlDate(new Date(2012 - 1900, 11, 12));

        HashMap<String, Object> populatedData = new HashMap<String, Object>();
        populatedData.put("sqlDateWrapper", sqlDateWrapper);

        TypeResolver typeResolver = mock(TypeResolver.class);

        FactFieldValueVerifier verifier = new FactFieldValueVerifier(
                populatedData,
                "sqlDateWrapper",
                sqlDateWrapper,
                typeResolver,
                Thread.currentThread().getContextClassLoader());

        List<VerifyField> fieldValues = new ArrayList<VerifyField>();
        VerifyField verifyField = new VerifyField("sqlDate", "12-DEC-2012", "==");
        fieldValues.add(verifyField);

        verifier.checkFields(fieldValues);

        assertTrue(verifyField.getSuccessResult());
    }
}
