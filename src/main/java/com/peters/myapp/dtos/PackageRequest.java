package com.peters.myapp.dtos;

import com.peters.myapp.models.Package;

public record PackageRequest(Package _package, long businessId) {
}
