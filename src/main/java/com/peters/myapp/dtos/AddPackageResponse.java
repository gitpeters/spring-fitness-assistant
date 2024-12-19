package com.peters.myapp.dtos;

import com.peters.myapp.models.Business;
import com.peters.myapp.models.Package;

import java.io.Serializable;

public record AddPackageResponse(Package _package, Business business) implements Serializable {
}
