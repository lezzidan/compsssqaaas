/*
 *  Copyright 2002-2022 Barcelona Supercomputing Center (www.bsc.es)
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */
package es.bsc.compss.types.annotations.task;

import es.bsc.compss.types.annotations.Constants;
import es.bsc.compss.types.annotations.Constraints;
import es.bsc.compss.types.annotations.parameter.Direction;
import es.bsc.compss.types.annotations.parameter.OnFailure;
import es.bsc.compss.types.annotations.task.repeatables.Methods;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Repeatable(Methods.class)
/**
 * Methods definition
 */
public @interface Method {

    /*
     * METHOD DEFINITION
     * 
     */

    /**
     * Returns the declaring class of the method.
     * 
     * @return method's declaring class.
     */
    String declaringClass();

    /**
     * Returns the name of the method.
     * 
     * @return method's name.
     */
    String name() default Constants.UNASSIGNED;

    /*
     * METHOD PROPERTIES
     * 
     */

    /**
     * Returns the access mode of the target object.
     * 
     * @return Direction object indicating the access mode of the target object.
     */
    Direction targetDirection() default Direction.INOUT;

    /*
     * COMMON PROPERTIES
     * 
     */

    /**
     * Returns if the method has priority or not.
     * 
     * @return if the method has priority or not.
     */
    String priority() default Constants.IS_NOT_PRIORITARY_TASK;

    /**
     * Returns the method specific constraints.
     * 
     * @return the method specific constraints.
     */
    Constraints constraints() default @Constraints();

    /**
     * Returns the method behavior on failure.
     * 
     * @return if the method has priority or not.
     */
    OnFailure onFailure() default OnFailure.RETRY;

    /**
     * Returns the time out set for execution.
     * 
     * @return The time set for the time out.
     */
    String timeOut() default "0";

}
