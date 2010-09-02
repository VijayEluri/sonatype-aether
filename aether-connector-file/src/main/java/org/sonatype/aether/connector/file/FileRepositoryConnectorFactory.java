package org.sonatype.aether.connector.file;

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

import org.codehaus.plexus.component.annotations.Component;
import org.codehaus.plexus.component.annotations.Requirement;
import org.sonatype.aether.RepositorySystemSession;
import org.sonatype.aether.repository.RemoteRepository;
import org.sonatype.aether.spi.connector.RepositoryConnector;
import org.sonatype.aether.spi.connector.RepositoryConnectorFactory;
import org.sonatype.aether.spi.log.Logger;
import org.sonatype.aether.spi.log.NullLogger;
import org.sonatype.aether.transfer.NoRepositoryConnectorException;

/**
 * Factory creating {@link FileRepositoryConnector}s.
 * 
 * 
 * @author Benjamin Hanzelmann
 */
@Component( role = RepositoryConnectorFactory.class, hint = "file" )
public class FileRepositoryConnectorFactory
    implements RepositoryConnectorFactory
{

    @Requirement
    private Logger logger = NullLogger.INSTANCE;

    private static final int FRCF_PRIORITY = 1;

    public static final String CFG_PREFIX = "aether.connector.file";

    public RepositoryConnector newInstance( RepositorySystemSession session, RemoteRepository repository )
        throws NoRepositoryConnectorException
    {

        if ( "file".equalsIgnoreCase( repository.getProtocol() ) )
        {
            FileRepositoryConnector connector = new FileRepositoryConnector( session, repository, logger );
            return connector;
        }
        else
            throw new NoRepositoryConnectorException( repository );

    }

    public int getPriority()
    {
        return FRCF_PRIORITY;
    }

}