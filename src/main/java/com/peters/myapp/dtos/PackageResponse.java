package com.peters.myapp.dtos;

import com.peters.myapp.models.Package;

import java.util.List;

public record PackageResponse(List<Package> packages) {
}
