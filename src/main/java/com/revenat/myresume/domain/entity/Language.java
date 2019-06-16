package com.revenat.myresume.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "f_language")
public class Language extends AbstractEntity<Long> implements ProfileEntity {
	private static final long serialVersionUID = -5638604441701746453L;
	
	@Id
	@SequenceGenerator(name = "language_seq_generator", sequenceName = "language_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "language_seq_generator")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "profile_id", nullable = false)
	private Profile profile;
	
	@Column(length = 60, nullable = false)
	private String name;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(nullable = false)
	private LanguageType type;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(nullable = false)
	private LanguageLevel level;

	@Override
	public Long getId() {
		return id;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LanguageType getType() {
		return type;
	}

	public void setType(LanguageType type) {
		this.type = type;
	}

	public LanguageLevel getLevel() {
		return level;
	}

	public void setLevel(LanguageLevel level) {
		this.level = level;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
