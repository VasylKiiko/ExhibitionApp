package com.Kiiko.ExhibitionsApp.api;

import com.Kiiko.ExhibitionsApp.controller.model.ExhibitionModel;
import com.Kiiko.ExhibitionsApp.dto.ExhibitionDto;
import com.Kiiko.ExhibitionsApp.model.SearchDetails;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Exhibition management API")
@RequestMapping("/api/v1/exhibitions")
public interface ExhibitionApi {
    @ApiImplicitParams({
            @ApiImplicitParam(name = "exhibitionId", paramType = "path", required = true, value = "Exhibition ID", dataType = "Integer")
    })
    @ApiOperation("Get exposition details by ID")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{exhibitionId}")
    ExhibitionModel getExhibitionDetails(@PathVariable int exhibitionId);

    @ApiOperation("Get exposition list filtered according to SearchDetails")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/search")
    List<ExhibitionModel> getFilteredExhibitions(@Valid @RequestBody SearchDetails searchDetails);

    @ApiOperation("Create new exhibition")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    ExhibitionModel createExhibition(@RequestBody ExhibitionDto exhibitionDto);

    @ApiImplicitParams({
            @ApiImplicitParam(name = "exhibitionId", paramType = "path", required = true, value = "Exhibition ID", dataType = "Integer"),
    })
    @ApiOperation("Update (replace) exhibition with ID with updated exhibitionDto")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{exhibitionId}")
    ExhibitionModel updateExhibition(@PathVariable int exhibitionId, @RequestBody ExhibitionDto exhibition);

    @ApiImplicitParams({
            @ApiImplicitParam(name = "exhibitionId", paramType = "path", required = true, value = "Exhibition ID", dataType = "Integer")
    })
    @ApiOperation("Delete exposition with ID")
    @DeleteMapping("/{exhibitionId}")
    ResponseEntity<Void> deleteExhibition(@PathVariable int exhibitionId);
}
