/*
 * Copyright (c)  2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package io.siddhi.extension.execution.math;

import io.siddhi.annotation.Example;
import io.siddhi.annotation.Extension;
import io.siddhi.annotation.Parameter;
import io.siddhi.annotation.ReturnAttribute;
import io.siddhi.annotation.util.DataType;
import io.siddhi.core.config.SiddhiQueryContext;
import io.siddhi.core.executor.ExpressionExecutor;
import io.siddhi.core.executor.function.FunctionExecutor;
import io.siddhi.core.util.config.ConfigReader;
import io.siddhi.core.util.snapshot.state.State;
import io.siddhi.core.util.snapshot.state.StateFactory;
import io.siddhi.query.api.definition.Attribute;
import io.siddhi.query.api.exception.SiddhiAppValidationException;

import static io.siddhi.extension.execution.math.util.MathUtil.convertToDouble;

/**
 * asin(a);
 * Returns the arc sin(inverse sine) of a if -1&lt;=a&lt;=1 or NULL otherwise. The return value is in radian scale.
 * Accept Type(s) :DOUBLE/FLOAT
 * Return Type(s): DOUBLE
 */
@Extension(
        name = "asin",
        namespace = "math",
        description = "If -1 <= p1 <= 1, this function returns the arc-sin (inverse sine) value of p1. " +
                "If the domain is invalid, it returns NULL. The value returned is in radian scale. " +
                "This function wraps the java.lang.Math.asin() function.",
        parameters = {
                @Parameter(
                        name = "p1",
                        description = "The value of the parameter whose arc-sin (inverse sine) value is found.",
                        type = {DataType.FLOAT, DataType.DOUBLE})
        },
        returnAttributes = @ReturnAttribute(
                description = "The arc-sin (inverse sine) value of the input parameter. Output is in radian scale.",
                type = {DataType.DOUBLE}),
        examples = @Example(

                syntax = "define stream InValueStream (inValue double); \n" +
                        "from InValueStream \n" +
                        "select math:asin(inValue) as asinValue \n" +
                        "insert into OutMediationStream;",
                description = "If the 'inValue' in the input stream is given, the function calculates the arc-sin " +
                        "value of it and returns the arc-sin value to the output stream, OutMediationStream. For "  +
                        "example, asin(0.5) returns 0.5235987755982989.")
)
public class AsinFunctionExtension extends FunctionExecutor {
    @Override
    protected StateFactory init(ExpressionExecutor[] expressionExecutors, ConfigReader configReader,
                                SiddhiQueryContext siddhiQueryContext) {
        if (attributeExpressionExecutors.length != 1) {
            throw new SiddhiAppValidationException("Invalid no of arguments passed to math:asin() function, " +
                    "required 1, but found " + attributeExpressionExecutors.length);
        }
        Attribute.Type attributeType = attributeExpressionExecutors[0].getReturnType();
        if (!((attributeType == Attribute.Type.FLOAT)
                || (attributeType == Attribute.Type.DOUBLE))) {
            throw new SiddhiAppValidationException("Invalid parameter type found for the argument of math:asin() " +
                    "function, required " + Attribute.Type.FLOAT + " or " + Attribute.Type.DOUBLE +
                    " but found " + attributeType.toString());
        }
        return null;
    }

    @Override
    protected Object execute(Object[] data, State state) {
        return null;    // This method won't get called. Hence, unimplemented.
    }


    @Override
    protected Object execute(Object data, State state) {
        if (data != null) {
            return Math.asin(convertToDouble(data));
        }
        return null;
    }

    @Override
    public Attribute.Type getReturnType() {
        return Attribute.Type.DOUBLE;
    }

}
