package org.sonatype.aether.util.listener;

/*
 * Copyright (c) 2010 Sonatype, Inc. All rights reserved.
 *
 * This program is licensed to you under the Apache License Version 2.0, 
 * and you may not use this file except in compliance with the Apache License Version 2.0. 
 * You may obtain a copy of the Apache License Version 2.0 at http://www.apache.org/licenses/LICENSE-2.0.
 *
 * Unless required by applicable law or agreed to in writing, 
 * software distributed under the Apache License Version 2.0 is distributed on an 
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the Apache License Version 2.0 for the specific language governing permissions and limitations there under.
 */

import java.io.File;
import java.util.Collections;
import java.util.List;

import org.sonatype.aether.RepositoryEvent;
import org.sonatype.aether.RepositorySystemSession;
import org.sonatype.aether.artifact.Artifact;
import org.sonatype.aether.metadata.Metadata;
import org.sonatype.aether.repository.ArtifactRepository;

/**
 * A simple repository event.
 * 
 * @author Benjamin Bentmann
 */
public class DefaultRepositoryEvent
    implements RepositoryEvent
{

    private EventType type;

    private RepositorySystemSession session;

    private Artifact artifact;

    private Metadata metadata;

    private ArtifactRepository repository;

    private File file;

    private List<Exception> exceptions = Collections.emptyList();

    public DefaultRepositoryEvent( EventType type, RepositorySystemSession session )
    {
        setType( type );
        setSession( session );
    }

    public EventType getType()
    {
        return type;
    }

    /**
     * Sets the type of the event.
     * 
     * @param type The type of the event, must not be {@code null}.
     * @return This event for chaining, never {@code null}.
     */
    private DefaultRepositoryEvent setType( EventType type )
    {
        if ( type == null )
        {
            throw new IllegalArgumentException( "event type not specified" );
        }
        this.type = type;
        return this;
    }

    public RepositorySystemSession getSession()
    {
        return session;
    }

    private DefaultRepositoryEvent setSession( RepositorySystemSession session )
    {
        if ( session == null )
        {
            throw new IllegalArgumentException( "session not specified" );
        }
        this.session = session;
        return this;
    }

    public Artifact getArtifact()
    {
        return artifact;
    }

    /**
     * Sets the artifact involved in the event.
     * 
     * @param file The involved artifact, may be {@code null}.
     * @return This event for chaining, never {@code null}.
     */
    public DefaultRepositoryEvent setArtifact( Artifact artifact )
    {
        this.artifact = artifact;
        return this;
    }

    public Metadata getMetadata()
    {
        return metadata;
    }

    /**
     * Sets the metadata involved in the event.
     * 
     * @param file The involved metadata, may be {@code null}.
     * @return This event for chaining, never {@code null}.
     */
    public DefaultRepositoryEvent setMetadata( Metadata metadata )
    {
        this.metadata = metadata;
        return this;
    }

    public ArtifactRepository getRepository()
    {
        return repository;
    }

    /**
     * Sets the repository involved in the event.
     * 
     * @param file The involved repository, may be {@code null}.
     * @return This event for chaining, never {@code null}.
     */
    public DefaultRepositoryEvent setRepository( ArtifactRepository repository )
    {
        this.repository = repository;
        return this;
    }

    public File getFile()
    {
        return file;
    }

    /**
     * Sets the file involved in the event.
     * 
     * @param file The involved file, may be {@code null}.
     * @return This event for chaining, never {@code null}.
     */
    public DefaultRepositoryEvent setFile( File file )
    {
        this.file = file;
        return this;
    }

    public Exception getException()
    {
        return exceptions.isEmpty() ? null : exceptions.get( 0 );
    }

    /**
     * Sets the exception causing the event.
     * 
     * @param exception The exception causing the event, may be {@code null}.
     * @return This event for chaining, never {@code null}.
     */
    public DefaultRepositoryEvent setException( Exception exception )
    {
        if ( exception != null )
        {
            this.exceptions = Collections.singletonList( exception );
        }
        else
        {
            this.exceptions = Collections.emptyList();
        }
        return this;
    }

    public List<Exception> getExceptions()
    {
        return exceptions;
    }

    /**
     * Sets the exceptions causing the event.
     * 
     * @param exceptions The exceptions causing the event, may be {@code null}.
     * @return This event for chaining, never {@code null}.
     */
    public DefaultRepositoryEvent setExceptions( List<Exception> exceptions )
    {
        if ( exceptions != null )
        {
            this.exceptions = exceptions;
        }
        else
        {
            this.exceptions = Collections.emptyList();
        }
        return this;
    }

}
