package com.bikkadit.electronicstrore.validation;

import javax.validation.Payload;

public @interface ImageNameValid {
    String message() default "Invalid image name !! ";

    Class<?>[] groups() default {};

    //Additional information about the annotation
    Class< ?extends Payload>[] payload() default{};
}
