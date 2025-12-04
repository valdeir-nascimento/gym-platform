package io.github.gym.platform.api.presentation.rest.controller.member;

import io.github.gym.platform.api.application.member.MemberItemOutput;
import io.github.gym.platform.api.application.member.register.MemberRegisterOutput;
import io.github.gym.platform.api.application.member.register.RegisterMemberUseCase;
import io.github.gym.platform.api.application.member.register.command.RegisterMemberCommand;
import io.github.gym.platform.api.application.member.retrieve.GetMemberByIdUseCase;
import io.github.gym.platform.api.application.member.retrieve.MembersByAcademyUseCase;
import io.github.gym.platform.api.application.member.retrieve.query.GetMemberByIdQuery;
import io.github.gym.platform.api.application.member.retrieve.query.ListMembersQuery;
import io.github.gym.platform.api.application.member.update.UpdateMemberUseCase;
import io.github.gym.platform.api.application.member.update.command.UpdateMemberCommand;
import io.github.gym.platform.api.infrastructure.security.annotation.RoleAdminOrTeacher;
import io.github.gym.platform.api.presentation.rest.controller.member.request.RegisterMemberRequest;
import io.github.gym.platform.api.presentation.rest.controller.member.request.UpdateMemberRequest;
import io.github.gym.platform.api.presentation.rest.controller.member.response.MemberResponse;
import io.github.gym.platform.api.presentation.rest.helper.ApiUriFactory;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/members")
public class MemberController {

    private final RegisterMemberUseCase registerMemberUseCase;
    private final MembersByAcademyUseCase membersByAcademyUseCase;
    private final GetMemberByIdUseCase getMemberByIdUseCase;
    private final UpdateMemberUseCase updateMemberUseCase;

    public MemberController(
        final RegisterMemberUseCase registerMemberUseCase,
        final MembersByAcademyUseCase membersByAcademyUseCase,
        final GetMemberByIdUseCase getMemberByIdUseCase,
        final UpdateMemberUseCase updateMemberUseCase
    ) {
        this.registerMemberUseCase = registerMemberUseCase;
        this.membersByAcademyUseCase = membersByAcademyUseCase;
        this.getMemberByIdUseCase = getMemberByIdUseCase;
        this.updateMemberUseCase = updateMemberUseCase;
    }

    @GetMapping
    @RoleAdminOrTeacher
    public ResponseEntity<List<MemberItemOutput>> listMembers(@RequestParam final String academyId) {
        final var outputs = membersByAcademyUseCase.execute(ListMembersQuery.with(academyId));
        return ResponseEntity.ok(outputs);
    }

    @GetMapping("/{memberId}")
    @RoleAdminOrTeacher
    public ResponseEntity<MemberResponse> getMember(@PathVariable final String memberId) {
        final var output = getMemberByIdUseCase.execute(GetMemberByIdQuery.with(memberId));
        return ResponseEntity.ok(MemberResponse.from(output));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MemberRegisterOutput> registerMember(@RequestBody @Valid final RegisterMemberRequest request) {
        final var command = RegisterMemberCommand.with(
            request.fullName(),
            request.email(),
            request.phone(),
            request.cpf(),
            request.birthDate(),
            request.planId(),
            request.password()
        );

        final var output = registerMemberUseCase.execute(command);
        final var location = ApiUriFactory.createdLocation("/members/{memberId}", output.id());
        return ResponseEntity.created(location).body(output);
    }

    @PutMapping("/{memberId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MemberResponse> updateMember(@PathVariable final String memberId, @RequestBody @Valid final UpdateMemberRequest request) {
        final var command = UpdateMemberCommand.with(
            memberId,
            request.fullName(),
            request.email(),
            request.phone(),
            request.cpf(),
            request.birthDate(),
            request.planId()
        );

        final var output = updateMemberUseCase.execute(command);
        return ResponseEntity.ok(MemberResponse.from(output));
    }
}

