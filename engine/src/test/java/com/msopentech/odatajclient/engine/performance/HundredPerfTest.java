/*
 * Copyright 2013 MS OpenTech.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.msopentech.odatajclient.engine.performance;

import java.io.IOException;

public class HundredPerfTest extends SinglePerfTest {

    @Override
    public void readAtomViaLowerlevelLibs() throws Exception {
        for (int i = 0; i < 100; i++) {
            super.readAtomViaLowerlevelLibs();
        }
    }

    @Override
    public void readAtomViaOdataJClient() {
        for (int i = 0; i < 100; i++) {
            super.readAtomViaOdataJClient();
        }
    }

    @Override
    public void writeAtomViaLowerlevelLibs() {
        for (int i = 0; i < 100; i++) {
            super.writeAtomViaLowerlevelLibs();
        }
    }

    @Override
    public void writeAtomViaOdataJClient() {
        for (int i = 0; i < 100; i++) {
            super.writeAtomViaOdataJClient();
        }
    }

    @Override
    public void readJSONViaLowerlevelLibs() throws IOException {
        for (int i = 0; i < 100; i++) {
            super.readJSONViaLowerlevelLibs();
        }
    }

    @Override
    public void readJSONViaOdataJClient() {
        for (int i = 0; i < 100; i++) {
            super.readJSONViaOdataJClient();
        }
    }

    @Override
    public void writeJSONViaLowerlevelLibs() {
        for (int i = 0; i < 100; i++) {
            super.writeJSONViaLowerlevelLibs();
        }
    }

    @Override
    public void writeJSONViaOdataJClient() {
        for (int i = 0; i < 100; i++) {
            super.writeJSONViaOdataJClient();
        }
    }
}
