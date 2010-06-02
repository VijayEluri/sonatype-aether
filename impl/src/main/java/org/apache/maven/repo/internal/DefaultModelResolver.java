package org.apache.maven.repo.internal;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.maven.model.Repository;
import org.apache.maven.model.building.FileModelSource;
import org.apache.maven.model.building.ModelSource;
import org.apache.maven.model.resolution.InvalidRepositoryException;
import org.apache.maven.model.resolution.ModelResolver;
import org.apache.maven.model.resolution.UnresolvableModelException;
import org.apache.maven.repo.Artifact;
import org.apache.maven.repo.ArtifactRequest;
import org.apache.maven.repo.ArtifactResolutionException;
import org.apache.maven.repo.DefaultArtifact;
import org.apache.maven.repo.RemoteRepository;
import org.apache.maven.repo.RepositorySession;
import org.apache.maven.repo.spi.ArtifactResolver;
import org.apache.maven.repo.spi.RemoteRepositoryManager;

/**
 * @author Benjamin Bentmann
 */
class DefaultModelResolver
    implements ModelResolver
{

    private final RepositorySession session;

    private final String context;

    private List<RemoteRepository> repositories;

    private final ArtifactResolver resolver;

    private final RemoteRepositoryManager remoteRepositoryManager;

    private final Set<String> repositoryIds;

    public DefaultModelResolver( RepositorySession session, String context, ArtifactResolver resolver,
                                 RemoteRepositoryManager remoteRepositoryManager, List<RemoteRepository> repositories )
    {
        this.session = session;
        this.context = context;
        this.resolver = resolver;
        this.remoteRepositoryManager = remoteRepositoryManager;
        this.repositories = repositories;
        this.repositoryIds = new HashSet<String>();
    }

    private DefaultModelResolver( DefaultModelResolver original )
    {
        this.session = original.session;
        this.context = original.context;
        this.resolver = original.resolver;
        this.remoteRepositoryManager = original.remoteRepositoryManager;
        this.repositories = original.repositories;
        this.repositoryIds = new HashSet<String>( original.repositoryIds );
    }

    public void addRepository( Repository repository )
        throws InvalidRepositoryException
    {
        if ( !repositoryIds.add( repository.getId() ) )
        {
            return;
        }

        RemoteRepository remoteRepository = DefaultArtifactDescriptorReader.convert( repository );

        this.repositories =
            remoteRepositoryManager.aggregateRepositories( session, repositories,
                                                           Collections.singletonList( remoteRepository ) );
    }

    public ModelResolver newCopy()
    {
        return new DefaultModelResolver( this );
    }

    public ModelSource resolveModel( String groupId, String artifactId, String version )
        throws UnresolvableModelException
    {
        Artifact pomArtifact = new DefaultArtifact( groupId, artifactId, "", "pom", version );

        try
        {
            ArtifactRequest request = new ArtifactRequest( pomArtifact, repositories, context );
            resolver.resolveArtifact( session, request );
        }
        catch ( ArtifactResolutionException e )
        {
            throw new UnresolvableModelException( "Failed to resolve POM for " + groupId + ":" + artifactId + ":"
                + version + " due to " + e.getMessage(), groupId, artifactId, version, e );
        }

        return new FileModelSource( pomArtifact.getFile() );
    }

}