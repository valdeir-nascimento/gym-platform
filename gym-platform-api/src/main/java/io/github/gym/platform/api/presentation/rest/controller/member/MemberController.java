package io.github.gym.platform.api.presentation.rest.controller.member;

import io.github.gym.platform.api.application.member.register.RegisterMemberUseCase;
import io.github.gym.platform.api.application.member.register.command.RegisterMemberCommand;
import io.github.gym.platform.api.application.member.retrieve.ListMembersUseCase;
import io.github.gym.platform.api.application.member.retrieve.query.ListMembersQuery;
import io.github.gym.platform.api.infrastructure.security.annotation.RoleAdminOrTeacher;
import io.github.gym.platform.api.presentation.rest.controller.member.request.RegisterMemberRequest;
import io.github.gym.platform.api.presentation.rest.controller.member.response.MemberResponse;
import io.github.gym.platform.api.presentation.rest.helper.ApiUriFactory;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/members")
public class MemberController {

    private final RegisterMemberUseCase registerMemberUseCase;
    private final ListMembersUseCase listMembersUseCase;

    public MemberController(
        final RegisterMemberUseCase registerMemberUseCase,
        final ListMembersUseCase listMembersUseCase
    ) {
        this.registerMemberUseCase = registerMemberUseCase;
        this.listMembersUseCase = listMembersUseCase;
    }

    @GetMapping
    @RoleAdminOrTeacher
    public ResponseEntity<List<MemberResponse>> listMembers(@RequestParam(required = false) final String academyId) {
        final var outputs = listMembersUseCase.execute(ListMembersQuery.with(academyId));
        final var response = outputs.stream().map(MemberResponse::from).toList();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MemberResponse> registerMember(@RequestBody @Valid final RegisterMemberRequest request) {
        final var command = RegisterMemberCommand.with(
            request.fullName(),
            request.email(),
            request.phone(),
            request.planId(),
            request.password()
        );

        final var output = registerMemberUseCase.execute(command);
        final var location = ApiUriFactory.createdLocation("/members/{memberId}", output.id());
        return ResponseEntity.created(location).body(MemberResponse.from(output));
    }
}

