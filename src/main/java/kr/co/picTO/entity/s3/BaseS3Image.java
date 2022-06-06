package kr.co.picTO.entity.s3;

import kr.co.picTO.entity.BaseTimeEntity;
import kr.co.picTO.entity.local.BaseLocalUser;
import kr.co.picTO.entity.oauth2.BaseAuthUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Builder
@Entity
@Table(name = "base_s3_image")
@NoArgsConstructor
@AllArgsConstructor
public class BaseS3Image extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = BaseAuthUser.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "bau_email")
    private BaseAuthUser bau;

    @ManyToOne(targetEntity = BaseLocalUser.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "blu_email")
    private BaseLocalUser blu;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String fileUrl;

    @Column(nullable = false)
    private String extension;

    @Column(nullable = false)
    private String provider;
}
